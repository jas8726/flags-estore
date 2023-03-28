import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Flag } from '../flag';
import { FlagService } from '../flag.service';
import { AccountService } from '../account.service';

@Component({
  selector: 'app-flag-detail',
  templateUrl: './flag-detail.component.html',
  styleUrls: [ './flag-detail.component.css' ]
})
export class FlagDetailComponent implements OnInit {
  flag: Flag | undefined;

  constructor(
    private route: ActivatedRoute,
    private flagService: FlagService,
    private location: Location,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.getFlag();
  }

  getFlag(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.flagService.getFlag(id)
      .subscribe(flag => this.flag = flag);
  }

  goBack(): void {
    this.location.back();
  }

  isAdmin(): boolean {
    return this.accountService.isAdmin();
  }

  save(): void {
    if (this.flag) {
      this.flagService.updateFlag(this.flag)
        .subscribe(() => this.goBack());
    }
  }
}