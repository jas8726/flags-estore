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
    public accountService: AccountService) {}

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

  checkBox( color: string ): string[] {
    
    var checkbox = document.getElementById( color ) as HTMLInputElement | null;
    
    console.log( document.getElementById( color ) );
    console.log( checkbox );

    if ( checkbox?.checked) {
      console.log('Checkbox is checked', color);
      this.tagFlags.push( color );
    }
    else{
      console.log("pppppppppppppppppppppppppppppppppppp")
    }
    console.log( this.tagFlags );
    return this.tagFlags;
  }


  checkboxesDataList = [
    {
      id: 'search-tag-green',
      label: 'Green',
      tag: 'green',
      isChecked: false
    },
    {
      id: 'search-tag-blue',
      label: 'Blue',
      tag: 'blue',
      isChecked: false
    },
    {
      id: 'search-tag-black',
      label: 'Black',
      tag: 'black',
      isChecked: false
    },
    {
      id: 'search-tag-yellow',
      label: 'Yellow',
      tag: 'yellow',
      isChecked: false
    },
    {
      id: 'search-tag-orange',
      label: 'Orange',
      tag: 'orange',
      isChecked: false
    },
    {
      id: 'search-tag-red',
      label: 'Red',
      tag: 'red',
      isChecked: false
    }
  ]
}