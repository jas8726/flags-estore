import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Flag } from './flag';
import { MessageService } from './message.service';


@Injectable({ providedIn: 'root' })
export class FlagService {

  private flagsUrl = 'http://localhost:8080/flags';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET flags from the server */
  getFlags(): Observable<Flag[]> {
    return this.http.get<Flag[]>(this.flagsUrl)
      .pipe(
        tap(_ => this.log('fetched flags')),
        catchError(this.handleError<Flag[]>('getFlags', []))
      );
  }

  /** GET flag by id. Return `undefined` when id not found */
  getFlagNo404<Data>(id: number): Observable<Flag> {
    const url = `${this.flagsUrl}/?id=${id}`;
    return this.http.get<Flag[]>(url)
      .pipe(
        map(flags => flags[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} flag id=${id}`);
        }),
        catchError(this.handleError<Flag>(`getFlag id=${id}`))
      );
  }

  /** GET flag by id. Will 404 if id not found */
  getFlag(id: number): Observable<Flag> {
    const url = `${this.flagsUrl}/${id}`;
    return this.http.get<Flag>(url).pipe(
      tap(_ => this.log(`fetched flag id=${id}`)),
      catchError(this.handleError<Flag>(`getFlag id=${id}`))
    );
  }

  /* GET flags whose name contains search term */
  searchFlags(term: string): Observable<Flag[]> {
    if (!term.trim()) {
      // if not search term, return empty flag array.
      return of([]);
    }
    return this.http.get<Flag[]>(`${this.flagsUrl}/?name=${term}`).pipe(
      tap(x => x.length ?
         this.log(`found flags matching "${term}"`) :
         this.log(`no flags matching "${term}"`)),
      catchError(this.handleError<Flag[]>('searchFlags', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new flag to the server */
  addFlag(flag: Flag): Observable<Flag> {
    return this.http.post<Flag>(this.flagsUrl, flag, this.httpOptions).pipe(
      tap((newFlag: Flag) => this.log(`added flag w/ id=${newFlag.id}`)),
      catchError(this.handleError<Flag>('addFlag'))
    );
  }

  /** DELETE: delete the flag from the server */
  deleteFlag(id: number): Observable<Flag> {
    const url = `${this.flagsUrl}/${id}`;

    return this.http.delete<Flag>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted flag id=${id}`)),
      catchError(this.handleError<Flag>('deleteFlag'))
    );
  }

  /** PUT: update the flag on the server */
  updateFlag(flag: Flag): Observable<any> {
    return this.http.put(this.flagsUrl, flag, this.httpOptions).pipe(
      tap(_ => this.log(`updated flag id=${flag.id}`)),
      catchError(this.handleError<any>('updateFlag'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a FlagService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`FlagService: ${message}`);
  }
}