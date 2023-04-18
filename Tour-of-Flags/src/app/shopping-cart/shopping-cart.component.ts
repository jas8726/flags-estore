import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Account } from '../account';
import { CartItem } from '../cart-item';
import { AccountService } from '../account.service';
import { Flag } from '../flag';
import { FlagService } from '../flag.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {
  currentAccount?: Account;
  cart: CartItem[] = [];
  errorText: String = "";

  constructor(
    private accountService: AccountService,
    private flagService: FlagService) {
    this.currentAccount = accountService.getCurrentAccount();
  }

  ngOnInit(): void {
    this.getFlags();
  }

  getFlags(): void {
    this.flagService.getFlags()
      .subscribe(_ => this.getCart());
  }

  getCart(): void {
    if (this.currentAccount) {
      this.accountService.getCart(this.currentAccount.username)
        .subscribe(cart => this.cart = cart ?? []);
    }
  }

  getFlagFromID(cartItem: CartItem): Flag | undefined {
    return this.flagService.allFlags.find(flag => (flag.id === cartItem.flagID));
  }

  add(id: number): void {
    if (this.currentAccount) {
      this.accountService.addFlagCart(this.currentAccount.username, id)
      .subscribe(() => {
        this.getCart();
      });
    }
  }

  delete(id: number): void {
    if (this.currentAccount) {
      this.accountService.deleteFlagCart(this.currentAccount.username, id)
      .subscribe(() => {
        this.getCart();
      });
    }
  }

  getTotalPrice(): number {
    var total = 0;
    for (let index = 0; index < this.cart.length; index++) {
      const element = this.cart[index];

      var flag = this.getFlagFromID(element);
      if (flag) {
        total += (flag.price * element.quantity);
      }
    }

    return total;
  }

  checkout(): void {
    for (let index = 0; index < this.cart.length; index++) {
      const element = this.cart[index];
      
      var flag = this.getFlagFromID(element);
      if (!flag) {
        this.delete(element.flagID);
        continue;
      }
      var newQuantity = flag.quantity - element.quantity;
      console.error("New quantity of " + flag.name + ": " + newQuantity);
      if (newQuantity < 0) {
        this.errorText = "You are trying to buy " + element.quantity + " " 
        + flag.name + " flags when there are only " + flag.quantity + " available.";
        return;
      }
      flag.quantity = newQuantity;
    }

    for (let index = 0; index < this.cart.length; index++) {
      const element = this.cart[index];
      
      this.flagService.updateFlag(this.getFlagFromID(element)!)
        .subscribe(() => {
          for (let index = 0; index < element.quantity; index++) {
            this.delete(element.flagID);
          }
        });
    }

    this.errorText = "Successfully checked out flags!"
  }
}
