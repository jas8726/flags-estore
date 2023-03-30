import { Component, OnInit } from '@angular/core';

import { Flag } from '../flag';
import { FlagService } from '../flag.service';
import { Account } from '../account';
import { AccountService } from '../account.service';

@Component({
  selector: 'app-flags',
  templateUrl: './flags.component.html',
  styleUrls: ['./flags.component.css']
})
export class FlagsComponent implements OnInit {
  flags: Flag[] = [];

  constructor(
    private flagService: FlagService,
    private accountService: AccountService) { }

  ngOnInit(): void {
    this.getFlags();
  }

  getFlags(): void {
    this.flagService.getFlags()
    .subscribe(flags => this.flags = flags);
  }

  isAdmin(): boolean {
    return this.accountService.isAdmin();
  }

  ableToAddToCart(): boolean {
    return (this.accountService.getCurrentAccount() != null) && !this.accountService.isAdmin();
  }

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.flagService.addFlag({ name } as Flag)
      .subscribe(flag => {
        this.flags.push(flag);
      });
  }

  delete(flag: Flag): void {
    this.flags = this.flags.filter(h => h !== flag);
    this.flagService.deleteFlag(flag.id).subscribe();
  }

  addToCart(flag: Flag): void {
    this.accountService.addFlagCart(this.accountService.getCurrentAccount()!.username, flag.id).subscribe();
  }
}