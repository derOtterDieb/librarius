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
  public newArmyList: IArmyListLbr;
  public showForm = false;

  constructor(private userService: UserService, private armyListService: ArmyListLbrService) {
    this.account = {};
    this.user = new User();
    this.armyList = new Array<ArmyListLbr>();
    this.newArmyList = new ArmyListLbr();
  }

  ngOnInit(): void {
    this.getContent();
  }

  private getContent(): void {
    this.getUser();
    this.getArmyList();
  }

  private getUser(): void {
    this.userService.find(this.account.login).subscribe(res => (this.user = res));
  }

  private getArmyList(): void {
    this.armyListService.getFromUserId(this.account.id).subscribe(res => {
      if (res.body != null) {
        this.armyList = res.body.filter(o => o != null);
      }
    });
  }

  public createArmyList(): void {
    this.armyListService.create(this.newArmyList).subscribe(res => {
      if (res.body != null) {
        this.newArmyList = res.body;
        if (this.user.armyListIds == null || this.user.armyListIds === undefined) {
          this.user.armyListIds = new Array<string>();
        }
        if (res.body.id != null) {
          this.user.armyListIds.push(res.body.id);
          this.userService.updateOneSelf(this.account.id, this.user).subscribe(() => this.getContent());
        }
      }
    });
    this.showForm = false;
  }

  public startCreatingNewList(): void {
    this.showForm = true;
    this.newArmyList = new ArmyListLbr();
  }

  public deleteListAndUpdateUser(listId: string): void {
    this.userService.deleteArmyList(listId, this.user).subscribe(
      res => (this.user = res),
      () => {},
      () => this.getArmyList()
    );
  }
}
