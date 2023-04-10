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
  tagFlags: string[] = [];
  public checkTester: boolean = false;

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
    var newFlag: Flag = { name } as Flag;
    newFlag.tags = [];
    this.flagService.addFlag(newFlag)
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

  /**
   * Looks at tag[] input and returns a FLag[] with all the flags that have the tag
   */
  tagFilter(flagTags: string[]): Flag[] {
    var flagList: Flag[] = [];

    if (!this.flags) {
      return flagList;
    }
    if (flagTags.length == 0) {   //if no checkboxes are selected
      return this.flags;
    }

    for (let i = 0; i < this.flags.length; i++) {
      const currentFlag = this.flags[i];
      for (let j = 0; j < flagTags.length; j++) {
        const currentTag = flagTags[j];

        // If one of the tags does not match, skip to the next flag.
        if (!currentFlag.tags.includes(currentTag)) break;

        if (j == flagTags.length - 1) {
          flagList.push(currentFlag);
        }
      }
    }

    return flagList;
  }

  checkBox(color: string): string[] {
    var checkbox = document.getElementById(color) as HTMLInputElement | null;
    if (checkbox?.checked) {
      this.tagFlags.push(color);
    }
    else {   //if the tag is unchecked
      if (this.tagFlags.includes(color)) {
        var pos: number = this.tagFlags.indexOf(color);
        this.tagFlags.splice(pos, 1);
      }
    }
    return this.tagFlags;
  }

  checkboxesDataList = [
    {
      id: 'green',
      label: 'Green',
      tag: 'green',
      isChecked: false
    },
    {
      id: 'blue',
      label: 'Blue',
      tag: 'blue',
      isChecked: false
    },
    {
      id: 'black',
      label: 'Black',
      tag: 'black',
      isChecked: false
    },
    {
      id: 'yellow',
      label: 'Yellow',
      tag: 'yellow',
      isChecked: false
    },
    {
      id: 'orange',
      label: 'Orange',
      tag: 'orange',
      isChecked: false
    },
    {
      id: 'red',
      label: 'Red',
      tag: 'red',
      isChecked: false
    },
    {
      id: 'white',
      label: 'White',
      tag: 'white',
      isChecked: false
    }
  ]
}