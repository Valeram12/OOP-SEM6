import {Injectable} from "@angular/core";
import {BACKEND_APIS} from "../util/backend-apis";
import {HttpClient} from "@angular/common/http";
import {tour} from "../../shared/models/Tour";
import {Observable} from "rxjs";
import {customerMembersWithtour} from "../../shared/models/CustomerMembersWithtour";
import {customerMember} from "../../shared/models/CustomerMember";
import {tourWithcustomerMembers} from "../../shared/models/TourWithcustomerMembers";

@Injectable({
  providedIn: "root"
})
export class tourService {
  private baseUrl = BACKEND_APIS.tourApi

  constructor(private httpClient:HttpClient) {
  }

  public getAlltours(): Observable<{tours: tour[]}> {
    return this.httpClient.get<{tours: tour[]}>(`${this.baseUrl}/list`);
  }

  public gettourById(id: number): Observable<tourWithcustomerMembers> {
    return this.httpClient.get<tourWithcustomerMembers>(`${this.baseUrl}/get/by-id?id=${id}`)
  }

  public createtour(toCreate: tour): Observable<tourWithcustomerMembers> {
    return this.httpClient.post<tourWithcustomerMembers>(`${this.baseUrl}/create`, toCreate)
  }

  public updatetour(toUpdate: tour): Observable<tourWithcustomerMembers> {
    return this.httpClient.put<tourWithcustomerMembers>(`${this.baseUrl}/update`, toUpdate);
  }

  public delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUrl}/delete?id=${id}`);
  }
}
