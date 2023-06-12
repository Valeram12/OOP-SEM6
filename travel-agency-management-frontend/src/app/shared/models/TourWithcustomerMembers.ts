import {customerMember} from "./CustomerMember";

export interface tourWithcustomerMembers {
  id: number;
  departureFrom: string;
  destination: string;
  departureTime: Date;
  arrivalTime: Date;
  crewMembers: customerMember[]
}
