import {HttpClient} from "@angular/common/http";
import {BACKEND_APIS} from "../util/backend-apis";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: "root"
})
export class RelationService {
  private baseUrl = BACKEND_APIS.relationApi;

  constructor(private httpClient:HttpClient) {
  }

  public createRelation(customerMemberId: number, tourId: number): Observable<void> {
    return this.httpClient.post<void>(`${this.baseUrl}/create?customerMemberId=${crewMemberId}&tourId=${tourId}`, null)
  }

  public deleteRelation(customerMemberId: number, tourId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUrl}/delete?customerMemberId=${crewMemberId}&tourId=${tourId}`)
  }
}
