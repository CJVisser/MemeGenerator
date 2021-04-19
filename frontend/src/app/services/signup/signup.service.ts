import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";
import { switchMap } from "rxjs/operators";
import { User } from "../../models/User";


@Injectable({
  providedIn: "root",
})
export class SignupService {

  showLoginForm: Boolean;
  response: any

  constructor(
    private httpClient: HttpClient,
  ) {
    
   }

  httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    }),
  };

  signup(user: User): Observable<any> {
    const e: Event = window.event;
    e.preventDefault();

    const environment = {
      production: false,
      apiUrl: "http://localhost:8080",
    };

    // return this.httpClient
    //   .post<User>(`${environment.apiUrl}/user`, user, this.httpOptions)
    //   .subscribe((res) => {

    //     this.showLoginForm = true
    //   });

    return this.httpClient
      .post<User>(`${environment.apiUrl}/user`, user, this.httpOptions)
  }

  // Error handling
  errorHandl(error) {
    let errorMessage = "";
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  }
}