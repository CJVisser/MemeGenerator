import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Meme } from "../../models/Meme";
import { environment } from "../../../environments/environment";
import { Observable, throwError } from "rxjs";
import { catchError, retry } from "rxjs/operators";
import { map, tap } from "rxjs/operators";

@Injectable({
  providedIn: "root",
})
export class MemeService {
  constructor(private http: HttpClient) {}

  httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    }),
  };

  httpOptionsWithResponse = {
    observe: "response" as const,
  };

  CreateMemeFormData(data: Meme): FormData {
    var result = new FormData();

    result.append("imageblob", data.imageblob);
    result.append("title", data.title);
    result.append("userId", data.userId.toString());
    result.append("tags", JSON.stringify(data.tags));
    
    if(data.description){
      result.append("description", data.description);
    }else{
      result.append("description", "");
    }
    result.append("categoryId", data.categoryId.toString());

    return result;
  }

  CreateMeme(data): Observable<any> {
    return this.http
      .post<Meme>(
        `${environment.apiUrl}/meme/`,
        this.CreateMemeFormData(data),
        this.httpOptionsWithResponse
      )
      .pipe(retry(1), catchError(this.handleError));
  }

  FlagMeme(id): void {

    const data = {
      Id: id
    }

    this.http.post(`${environment.apiUrl}/meme/flag`, JSON.stringify(data), this.httpOptions).subscribe(
      tap((response: any) => {

        if (response.status) {
          alert("You have flagged this meme!")
        } 
      })
    );
  }

  GetAllMemes(): Observable<Meme[]> {
    return this.http
      .get<Meme[]>(`${environment.apiUrl}/meme/`, this.httpOptions)
      .pipe(retry(1), catchError(this.handleError));
  }

  GetUserIsAllowedToCreateMeme(id): Observable<boolean> {
    return this.http
      .get<boolean>(`${environment.apiUrl}/meme/checkAllowed/` + id, this.httpOptions)
      .pipe(retry(1), catchError(this.handleError));
  }

  getMeme(id): Observable<Meme> {
    return this.http
      .get<Meme>(`${environment.apiUrl}/meme/` + id)
      .pipe(retry(1), catchError(this.handleError));
  }

  GetAllMemesFilteredOnCategory(category): Observable<Meme[]> {
    return this.http
    .get<Meme[]>(`${environment.apiUrl}/meme/category/` + category)
      .pipe(retry(1), catchError(this.handleError));
  }

  GetAllMemesFilteredOnTag(tag): Observable<Meme[]> {
    return this.http
    .get<Meme[]>(`${environment.apiUrl}/meme/tags/` + tag)
    .pipe(retry(1), catchError(this.handleError));
}
  
  updateMeme(meme: Meme) {
    this.http.put<Meme>(`${environment.apiUrl}/meme/${meme.id}`, meme);
  }

  // Error handling
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
