import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { User } from "../../models/User";
import { environment } from "../../../environments/environment";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class AdminService {

  constructor(
    protected httpClient: HttpClient,
  ) {
  }

  getUsers(): Observable<User[]> {
      return this.httpClient.get<User[]>(`${environment.apiUrl}/user/`);
  }

  banUser(userId: number): Observable<User> {
    return this.httpClient.put<User>(`${environment.apiUrl}/user/ban`, userId);
  }
}
