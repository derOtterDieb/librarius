import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { LibrariusTestModule } from '../../../test.module';
import { ArmyListLbrComponent } from 'app/entities/army-list-lbr/army-list-lbr.component';
import { ArmyListLbrService } from 'app/entities/army-list-lbr/army-list-lbr.service';
import { ArmyListLbr } from 'app/shared/model/army-list-lbr.model';

describe('Component Tests', () => {
  describe('ArmyListLbr Management Component', () => {
    let comp: ArmyListLbrComponent;
    let fixture: ComponentFixture<ArmyListLbrComponent>;
    let service: ArmyListLbrService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibrariusTestModule],
        declarations: [ArmyListLbrComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(ArmyListLbrComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArmyListLbrComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmyListLbrService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ArmyListLbr('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.armyLists && comp.armyLists[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ArmyListLbr('123')],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.armyLists && comp.armyLists[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
