import { Component } from '@angular/core';

import { Account } from '../account';
import { ShoppingCart } from '../shopping-cart';
import { AccountService } from '../account.service';
import { Flag } from '../flag';
import { FlagService } from '../flag.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent {
  currentAccount!: Account;
  cart: ShoppingCart | undefined;

  constructor(private accountService: AccountService, private flagService: FlagService) {
    if (accountService.getCurrentAccount()) {
      this.currentAccount = accountService.getCurrentAccount()!;
    }
   }

  ngOnInit(): void {
    this.getCart();
  }

  getCart(): void {
    if (this.currentAccount) {
      this.accountService.getCart(this.currentAccount.username)
      .subscribe(cart => this.cart = cart);
    }
  }



  //prob change output and the return
  getFlagFromID(id: number): any {
    // Need to figure this out
    return this.flagService.getFlagName( id ) + " hello";
  }

  add(id: number): void {
    this.accountService.addFlagCart(this.currentAccount.username, id)
      .subscribe(() => {
        this.getCart();
      });
  }

  delete(id: number): void {
    this.accountService.deleteFlagCart(this.currentAccount.username, id)
      .subscribe(() => {
        this.getCart();
      });
  }
}
