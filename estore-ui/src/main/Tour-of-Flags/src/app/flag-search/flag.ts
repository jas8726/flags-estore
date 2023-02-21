import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';

import { Flag } from '../flag';
import { FlagService } from '../flag.service';

@Component({
  selector: 'app-flag-search',
  templateUrl: './flag-search.component.html',
  styleUrls: [ './flag-search.component.css' ]
})
export class FlagSearchComponent implements OnInit {
  flags$!: Observable<Flag[]>;
  private searchTerms = new Subject<string>();

  constructor(private flagService: FlagService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.flags$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.flagService.searchFlags(term)),
    );
  }
}