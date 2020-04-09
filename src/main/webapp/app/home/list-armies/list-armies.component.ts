import { Component, OnInit, Input } from '@angular/core';
import { UserService } from 'app/core/user/user.service';
import { User } from 'app/core/user/user.model';
import { ArmyListLbrService } from 'app/entities/army-list-lbr/army-list-lbr.service';
import { IArmyListLbr, ArmyListLbr } from 'app/shared/model/army-list-lbr.model';

@Component({
  selector: 'jhi-list-armies',
  templateUrl: './list-armies.component.html',
  styleUrls: ['./list-armies.component.scss']
})
export class ListArmiesComponent implements OnInit {
  @Input() account: any;
  public user: User;
  public armyList: IArmyListLbr[];

  constructor(private userService: UserService, private armyListService: ArmyListLbrService) {
    this.account = {};
    this.user = new User();
    this.armyList = new Array<ArmyListLbr>();
  }

  ngOnInit(): void {
    this.getUser();
    this.getArmyList();
  }

  private getUser() {
    this.userService.find(this.account.login).subscribe(res => (this.user = res));
  }

  private getArmyList() {
    this.armyListService.getFromUserId(this.account.id).subscribe(res => (this.armyList = res));
  }
}
