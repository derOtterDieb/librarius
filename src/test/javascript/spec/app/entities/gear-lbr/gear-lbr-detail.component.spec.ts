import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LibrariusTestModule } from '../../../test.module';
import { GearLbrDetailComponent } from 'app/entities/gear-lbr/gear-lbr-detail.component';
import { GearLbr } from 'app/shared/model/gear-lbr.model';

describe('Component Tests', () => {
  describe('GearLbr Management Detail Component', () => {
    let comp: GearLbrDetailComponent;
    let fixture: ComponentFixture<GearLbrDetailComponent>;
    const route = ({ data: of({ gear: new GearLbr('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibrariusTestModule],
        declarations: [GearLbrDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GearLbrDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GearLbrDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gear on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gear).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
