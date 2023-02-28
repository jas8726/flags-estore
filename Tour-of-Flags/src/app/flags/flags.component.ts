import { Component, OnInit } from '@angular/core';

import { Flag } from '../flag';
import { FlagService } from '../flag.service';

@Component({
  selector: 'app-flags',
  templateUrl: './flags.component.html',
  styleUrls: ['./flags.component.css']
})
export class FlagsComponent implements OnInit {
  flags: Flag[] = [];

  constructor(private flagService: FlagService) { }

  ngOnInit(): void {
    this.getFlags();
  }

  getFlags(): void {
    this.flagService.getFlags()
    .subscribe(flags => this.flags = flags);
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

}