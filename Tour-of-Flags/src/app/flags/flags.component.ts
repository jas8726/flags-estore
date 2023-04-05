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
  testTags: string[] = [];

  constructor(
    private flagService: FlagService,
    public accountService: AccountService) { }

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

  tagFilter(flagTags: string[]): Flag[] {
    var flagList: Flag[] = [];
    if( !this.flags ) {
      return flagList;
    }

    if( flagTags.length == 0 ) {   //if no checkboxes are selected
      return this.flags;
    }
    
    for( let i = 0; i < this.flags.length; i++ ) {
      for( let j = 0; j < flagTags.length; j++ ) {
        if( this.flags[i].tags.includes( flagTags[j] ) ) {
          flagList.push( this.flags[i] );
          break;
        }
      }
    }
    return flagList;
  }

  checkBox(): string[] {
    var tags: string[] = [];

    const checkbox = document.getElementById(
      'search-tag-green',
    ) as HTMLInputElement | null;
    
    if (checkbox?.checked) {
      console.log('Checkbox is checked');
      tags.push( "green" );
    }
    
    return this.testTags;
  }
}