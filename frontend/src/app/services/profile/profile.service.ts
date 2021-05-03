import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { User } from "../../models/User";
import { environment } from "../../../environments/environment";
import { catchError, retry } from "rxjs/operators";
import { Observable } from "rxjs";
import { throwError } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class ProfileService {
  constructor(private http: HttpClient) {}

  httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    }),
  };

  updateUserInfo(user: User): Observable<User> {
    return this.http.put<User>(
      `${environment.apiUrl}/user`,
      user,
      this.httpOptions
    );
  }

  getUserInfo(userId = null): Observable<User> {
    if(!userId){
      userId = 0;
    }

    return this.http
      .get<User>(`${environment.apiUrl}/user/` + userId)
      .pipe(retry(1), catchError(this.handleError));
  }

  handleError(error) {
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
