import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LibrariusTestModule } from '../../../test.module';
import { ArmyListLbrUpdateComponent } from 'app/entities/army-list-lbr/army-list-lbr-update.component';
import { ArmyListLbrService } from 'app/entities/army-list-lbr/army-list-lbr.service';
import { ArmyListLbr } from 'app/shared/model/army-list-lbr.model';

describe('Component Tests', () => {
  describe('ArmyListLbr Management Update Component', () => {
    let comp: ArmyListLbrUpdateComponent;
    let fixture: ComponentFixture<ArmyListLbrUpdateComponent>;
    let service: ArmyListLbrService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibrariusTestModule],
        declarations: [ArmyListLbrUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ArmyListLbrUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArmyListLbrUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmyListLbrService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ArmyListLbr('123');
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
        const entity = new ArmyListLbr();
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
