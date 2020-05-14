export interface ISquadronLbr {
  id?: string;
  name?: string;
  userId?: string;
  listId?: string;
}

export class SquadronLbr implements ISquadronLbr {
  constructor(public id?: string, public name?: string, public userId?: string, public listId?: string) {}
}
