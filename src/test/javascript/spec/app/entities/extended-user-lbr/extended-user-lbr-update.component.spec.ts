import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LibrariusTestModule } from '../../../test.module';
import { ExtendedUserLbrUpdateComponent } from 'app/entities/extended-user-lbr/extended-user-lbr-update.component';
import { ExtendedUserLbrService } from 'app/entities/extended-user-lbr/extended-user-lbr.service';
import { ExtendedUserLbr } from 'app/shared/model/extended-user-lbr.model';

describe('Component Tests', () => {
  describe('ExtendedUserLbr Management Update Component', () => {
    let comp: ExtendedUserLbrUpdateComponent;
    let fixture: ComponentFixture<ExtendedUserLbrUpdateComponent>;
    let service: ExtendedUserLbrService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibrariusTestModule],
        declarations: [ExtendedUserLbrUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ExtendedUserLbrUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExtendedUserLbrUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExtendedUserLbrService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExtendedUserLbr('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExtendedUserLbr();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
