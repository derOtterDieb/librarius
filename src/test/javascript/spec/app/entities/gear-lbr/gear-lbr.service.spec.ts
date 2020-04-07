import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { GearLbrService } from 'app/entities/gear-lbr/gear-lbr.service';
import { IGearLbr, GearLbr } from 'app/shared/model/gear-lbr.model';

describe('Service Tests', () => {
  describe('GearLbr Service', () => {
    let injector: TestBed;
    let service: GearLbrService;
    let httpMock: HttpTestingController;
    let elemDefault: IGearLbr;
    let expectedResult: IGearLbr | IGearLbr[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(GearLbrService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new GearLbr('ID', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find('123').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a GearLbr', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new GearLbr()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GearLbr', () => {
        const returnedFromService = Object.assign(
          {
            gearName: 'BBBBBB',
            pointValue: 1,
            type: 'BBBBBB',
            f: 'BBBBBB',
            range: 'BBBBBB',
            pa: 'BBBBBB',
            d: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of GearLbr', () => {
        const returnedFromService = Object.assign(
          {
            gearName: 'BBBBBB',
            pointValue: 1,
            type: 'BBBBBB',
            f: 'BBBBBB',
            range: 'BBBBBB',
            pa: 'BBBBBB',
            d: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a GearLbr', () => {
        service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
