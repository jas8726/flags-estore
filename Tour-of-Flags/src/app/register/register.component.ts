import { Component } from '@angular/core';
import { Router } from '@angular/router';

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
    private router: Router
  ) {}

  register() {
    const newAccount = {username: this.username, password: this.password}
    this.accountService.addAccount(newAccount).subscribe((account) => {
      if (account) {
        this.router.navigate(['/dashboard']);
      }
    });
  }
}
