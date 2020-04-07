import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LibrariusTestModule } from '../../../test.module';
import { ExtendedUserLbrDetailComponent } from 'app/entities/extended-user-lbr/extended-user-lbr-detail.component';
import { ExtendedUserLbr } from 'app/shared/model/extended-user-lbr.model';

describe('Component Tests', () => {
  describe('ExtendedUserLbr Management Detail Component', () => {
    let comp: ExtendedUserLbrDetailComponent;
    let fixture: ComponentFixture<ExtendedUserLbrDetailComponent>;
    const route = ({ data: of({ extendedUser: new ExtendedUserLbr('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibrariusTestModule],
        declarations: [ExtendedUserLbrDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ExtendedUserLbrDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExtendedUserLbrDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load extendedUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.extendedUser).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
