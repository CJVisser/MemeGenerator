import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { User } from "../../models/User"
import { environment } from "../../../environments/environment";
import { BehaviorSubject, Observable } from "rxjs";
import { map, tap } from "rxjs/operators";
import { ProfileService } from "../profile/profile.service";
import { EventEmitter, Output } from '@angular/core';

export interface LoginResponse {
  status: boolean;
  userId: number;
}

@Injectable({
  providedIn: "root",
})
export class LoginService {
  private _loggedIn: BehaviorSubject<boolean>;
  private _currentUser: BehaviorSubject<User>;
  @Output() getLoggedInUser: EventEmitter<any> = new EventEmitter();

  constructor(
    protected httpClient: HttpClient,
    protected profileService: ProfileService
  ) {
    this._loggedIn = new BehaviorSubject<boolean>(false);
    this._currentUser = new BehaviorSubject<User>(null);
  }

  loggedIn(): Observable<boolean> {
    return this._loggedIn.asObservable();
  }

  currentUser(): Observable<User> {
    return this._currentUser.asObservable();
  }

  getLoggedIn(): boolean {
    return this._loggedIn.getValue();
  }

  getCurrentUser(): User {
    return this._currentUser.getValue();
  }

  login(username: string, password: string): Observable<boolean> {
    const formData = new FormData();
    formData.append("username", username);
    formData.append("password", password);
    return this.httpClient.post(`${environment.apiUrl}/login`, formData).pipe(
      tap((response: LoginResponse) => {
        this._loggedIn.next(response.status);

        if (response.status) {

          this.profileService
            .getUserInfo(response.userId)
            .subscribe((user: User) => {
              this._currentUser.next(user)

              this.getLoggedInUser.emit(user)
            });
        } else {

          alert("Verkeerde username of password")

          throw ("Wrong username");
        }
      }),
      map((response: LoginResponse) => response.status)
    );
  }

  logout(): void {

    this._loggedIn.next(false);
    this._currentUser.next(null);

    this.httpClient.post(`${environment.apiUrl}/logout`, {}).subscribe(
      tap((response: any) => {
      })
    );

    window.location.reload()
  }
}
