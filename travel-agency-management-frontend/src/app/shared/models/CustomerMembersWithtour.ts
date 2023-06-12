import {tour} from "./Tour";
import {customerMember} from "./CustomerMember";

export interface customerMembersWithtour extends customerMember {
  flights: tour[];
}
