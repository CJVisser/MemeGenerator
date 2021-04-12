import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { throwError } from "rxjs";
import { User } from "../../models/User";


@Injectable({
    providedIn: "root",
  })
export class SignupService {
  
    showLoginForm: Boolean;

    constructor(
        private httpClient: HttpClient,
      ) {}

    httpOptions = {
    headers: new HttpHeaders({
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
    }),
    };

    signup(user: User): void {
        const e: Event = window.event;
        e.preventDefault();

        const environment = {
        production: false,
        apiUrl: "http://localhost:8080",
        };

        this.httpClient
        .post<User>(`${environment.apiUrl}/user`, user, this.httpOptions)
        .subscribe((res) => {

            this.showLoginForm = true
            
            console.log(res);
        });
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