import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LibrariusTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ExtendedUserLbrDeleteDialogComponent } from 'app/entities/extended-user-lbr/extended-user-lbr-delete-dialog.component';
import { ExtendedUserLbrService } from 'app/entities/extended-user-lbr/extended-user-lbr.service';

describe('Component Tests', () => {
  describe('ExtendedUserLbr Management Delete Component', () => {
    let comp: ExtendedUserLbrDeleteDialogComponent;
    let fixture: ComponentFixture<ExtendedUserLbrDeleteDialogComponent>;
    let service: ExtendedUserLbrService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibrariusTestModule],
        declarations: [ExtendedUserLbrDeleteDialogComponent]
      })
        .overrideTemplate(ExtendedUserLbrDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExtendedUserLbrDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExtendedUserLbrService);
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
