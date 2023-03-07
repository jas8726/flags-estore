import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Account } from '../account'
import { AccountService } from '../account.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  account: Account | undefined;

  constructor(
    private route: ActivatedRoute,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.getFlag();
  }

  getFlag(): void {
    const username = this.route.snapshot.paramMap.get('username')!
    this.accountService.getAccount(username)
      .subscribe(account => this.account = account);
  }

}
