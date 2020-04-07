import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LibrariusTestModule } from '../../../test.module';
import { GearLbrUpdateComponent } from 'app/entities/gear-lbr/gear-lbr-update.component';
import { GearLbrService } from 'app/entities/gear-lbr/gear-lbr.service';
import { GearLbr } from 'app/shared/model/gear-lbr.model';

describe('Component Tests', () => {
  describe('GearLbr Management Update Component', () => {
    let comp: GearLbrUpdateComponent;
    let fixture: ComponentFixture<GearLbrUpdateComponent>;
    let service: GearLbrService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibrariusTestModule],
        declarations: [GearLbrUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GearLbrUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GearLbrUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GearLbrService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GearLbr('123');
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
        const entity = new GearLbr();
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
