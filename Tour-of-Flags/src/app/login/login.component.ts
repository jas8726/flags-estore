import { Component } from '@angular/core';
import { Router } from '@angular/router';

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
    private router: Router
  ) {}

  login() {
    this.accountService.loginAccount(this.username, this.password).subscribe((account) => {
      if (account) {
        this.router.navigate(['/dashboard']);
      }
    });
  }
}
