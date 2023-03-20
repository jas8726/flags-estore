import { Component } from '@angular/core';
import { Location } from '@angular/common';

import { AccountService } from '../account.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(
    private accountService: AccountService,
    private location: Location
  ) {}

  login() {
    this.accountService.loginAccount(this.username, this.password).subscribe((account) => {
      if (account) {
        this.location.back();
      }
    });
  }
}
