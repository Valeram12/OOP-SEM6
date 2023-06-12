import {Injectable} from "@angular/core";
import {BACKEND_APIS} from "../util/backend-apis";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {customerMember} from "../../shared/models/CustomerMember";
import {customerMembersWithtour} from "../../shared/models/CustomerMembersWithtour";

@Injectable({
  providedIn: "root"
})
export class customerMemberService {
  private baseUrl = BACKEND_APIS.crewMemberApi

  constructor(private httpClient: HttpClient) {
  }

  public getcustomerMembers(): Observable<{crewMembers: customerMember[]}> {
    return this.httpClient.get<{crewMembers: customerMember[]}>(`${this.baseUrl}/list`);
  }

  public getcustomerMemberById(id: number): Observable<customerMembersWithtour> {
    return this.httpClient.get<customerMembersWithtour>(`${this.baseUrl}/get/by-id?id=${id}`)
  }

  public createcustomerMember(toCreate: customerMember): Observable<customerMembersWithtour> {
    return this.httpClient.post<customerMembersWithtour>(`${this.baseUrl}/create`, toCreate)
  }

  public updatecustomerMember(toUpdate: customerMember): Observable<customerMembersWithtour> {
    return this.httpClient.put<customerMembersWithtour>(`${this.baseUrl}/update`, toUpdate);
  }

  public delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUrl}/delete?id=${id}`);
  }
}
