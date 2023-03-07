import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Account } from './account';
import { MessageService } from './message.service';


@Injectable({ providedIn: 'root' })
export class AccountService {

  private accountsUrl = 'http://localhost:8080/accounts';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET accounts from the server */
  getAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(this.accountsUrl)
      .pipe(
        tap(_ => this.log('fetched accounts')),
        catchError(this.handleError<Account[]>('getAccounts', []))
      );
  }

  /** GET account by username. Return `undefined` when username not found */
  getAccountNo404<Data>(username: string): Observable<Account> {
    const url = `${this.accountsUrl}/?username=${username}`;
    return this.http.get<Account[]>(url)
      .pipe(
        map(accounts => accounts[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} account username=${username}`);
        }),
        catchError(this.handleError<Account>(`getAccount username=${username}`))
      );
  }

  /** GET account by username. Will 404 if username not found */
  getAccount(username: string): Observable<Account> {
    const url = `${this.accountsUrl}/${username}`;
    return this.http.get<Account>(url).pipe(
      tap(_ => this.log(`fetched account username=${username}`)),
      catchError(this.handleError<Account>(`getAccount username=${username}`))
    );
  }

  /** GET account by username and password. Return `undefined` when account not found */
  loginAccountNo404<Data>(username: string, password: string): Observable<Account> {
    const url = `${this.accountsUrl}/?username=${username}?password=${password}`;
    return this.http.get<Account[]>(url)
      .pipe(
        map(accounts => accounts[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} account username=${username} password=${password}`);
        }),
        catchError(this.handleError<Account>(`getAccount username=${username} password=${password}`))
      );
  }

  /** GET account by username and password. Will 401 if account not found */
  loginAccount(username: string, password: string): Observable<Account> {
    const url = `${this.accountsUrl}/${username}/${password}`;
    return this.http.get<Account>(url).pipe(
      tap(_ => this.log(`fetched account username=${username} password=${password}`)),
      catchError(this.handleError<Account>(`getAccount username=${username} password=${password}`))
    );
  }

  //////// Save methods //////////

  /** POST: add a new account to the server */
  addAccount(account: Account): Observable<Account> {
    return this.http.post<Account>(this.accountsUrl, account, this.httpOptions).pipe(
      tap((newAccount: Account) => this.log(`added account w/ username=${newAccount.username}`)),
      catchError(this.handleError<Account>('addAccount'))
    );
  }

  /** DELETE: delete the account from the server */
  deleteAccount(username: string): Observable<Account> {
    const url = `${this.accountsUrl}/${username}`;

    return this.http.delete<Account>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted account username=${username}`)),
      catchError(this.handleError<Account>('deleteAccount'))
    );
  }

  /** PUT: update the account on the server */
  updateAccount(account: Account): Observable<any> {
    return this.http.put(this.accountsUrl, account, this.httpOptions).pipe(
      tap(_ => this.log(`updated account username=${account.username}`)),
      catchError(this.handleError<any>('updateAccount'))
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

  /** Log an AccountService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`AccountService: ${message}`);
  }
}