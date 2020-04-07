import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LibrariusTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ArmyListLbrDeleteDialogComponent } from 'app/entities/army-list-lbr/army-list-lbr-delete-dialog.component';
import { ArmyListLbrService } from 'app/entities/army-list-lbr/army-list-lbr.service';

describe('Component Tests', () => {
  describe('ArmyListLbr Management Delete Component', () => {
    let comp: ArmyListLbrDeleteDialogComponent;
    let fixture: ComponentFixture<ArmyListLbrDeleteDialogComponent>;
    let service: ArmyListLbrService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibrariusTestModule],
        declarations: [ArmyListLbrDeleteDialogComponent]
      })
        .overrideTemplate(ArmyListLbrDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArmyListLbrDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmyListLbrService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('123');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('123');
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
