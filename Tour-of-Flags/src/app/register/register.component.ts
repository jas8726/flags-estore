import { Component } from '@angular/core';
import { Location } from '@angular/common';

import { Account } from '../account'
import { AccountService } from '../account.service';

@Component({
  selector: 'app-login',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username: string = '';
  password: string = '';

  constructor(
    private accountService: AccountService,
    private location: Location
  ) {}

  register() {
    const newAccount = {username: this.username, password: this.password}
    this.accountService.addAccount(newAccount).subscribe((account) => {
      if (account) {
        this.location.back();
      }
    });
  }
}
