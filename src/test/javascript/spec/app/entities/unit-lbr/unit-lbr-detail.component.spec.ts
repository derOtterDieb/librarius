import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LibrariusTestModule } from '../../../test.module';
import { UnitLbrDetailComponent } from 'app/entities/unit-lbr/unit-lbr-detail.component';
import { UnitLbr } from 'app/shared/model/unit-lbr.model';

describe('Component Tests', () => {
  describe('UnitLbr Management Detail Component', () => {
    let comp: UnitLbrDetailComponent;
    let fixture: ComponentFixture<UnitLbrDetailComponent>;
    const route = ({ data: of({ unit: new UnitLbr('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibrariusTestModule],
        declarations: [UnitLbrDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UnitLbrDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UnitLbrDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load unit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.unit).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
