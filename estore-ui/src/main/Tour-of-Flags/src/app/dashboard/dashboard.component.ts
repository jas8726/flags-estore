import { Component, OnInit } from '@angular/core';
import { Flag } from '../flag';
import { FlagService } from '../flag.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  flags: Flag[] = [];

  constructor(private flagService: FlagService) { }

  ngOnInit(): void {
    this.getFlags();
  }

  getFlags(): void {
    this.flagService.getFlags()
      .subscribe(flags => this.flags = flags.slice(1, 5));
  }
}