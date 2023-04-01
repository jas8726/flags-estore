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
    this.getCart();
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

  checkout(): void {
    this.cart.forEach(cartItem => {
      var flag = this.getFlagFromID(cartItem);
      if (!flag) {
        this.errorText = "One of your flags does not exist!";
        return;
      }
      var newQuantity = flag.quantity - cartItem.quantity;
      if (newQuantity < 0) {
        this.errorText = "You are trying to buy " + cartItem.quantity + " " 
        + flag.name + " when there are only " + flag.quantity + " available.";
        return
      }
      flag.quantity = newQuantity;
    });

    this.errorText = "";

    this.cart.forEach(purchase => {
      this.flagService.updateFlag(this.getFlagFromID(purchase)!)
        .subscribe(() => {
          for (let index = 0; index < purchase.quantity; index++) {
            this.delete(purchase.flagID);
          }
        });
    });
    
    // GOTO CHECKOUT SCREEN
    this.errorText = "Checked out flags!"
  }
}
