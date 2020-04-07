import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LibrariusTestModule } from '../../../test.module';
import { ArmyListLbrDetailComponent } from 'app/entities/army-list-lbr/army-list-lbr-detail.component';
import { ArmyListLbr } from 'app/shared/model/army-list-lbr.model';

describe('Component Tests', () => {
  describe('ArmyListLbr Management Detail Component', () => {
    let comp: ArmyListLbrDetailComponent;
    let fixture: ComponentFixture<ArmyListLbrDetailComponent>;
    const route = ({ data: of({ armyList: new ArmyListLbr('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibrariusTestModule],
        declarations: [ArmyListLbrDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ArmyListLbrDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArmyListLbrDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load armyList on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.armyList).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
