import { Component, OnInit } from '@angular/core';

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

  constructor(private accountService: AccountService, private flagService: FlagService) {
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
}
