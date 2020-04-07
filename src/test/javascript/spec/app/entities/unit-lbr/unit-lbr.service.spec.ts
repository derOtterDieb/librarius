import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UnitLbrService } from 'app/entities/unit-lbr/unit-lbr.service';
import { IUnitLbr, UnitLbr } from 'app/shared/model/unit-lbr.model';

describe('Service Tests', () => {
  describe('UnitLbr Service', () => {
    let injector: TestBed;
    let service: UnitLbrService;
    let httpMock: HttpTestingController;
    let elemDefault: IUnitLbr;
    let expectedResult: IUnitLbr | IUnitLbr[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UnitLbrService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UnitLbr(
        'ID',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find('123').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UnitLbr', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UnitLbr()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UnitLbr', () => {
        const returnedFromService = Object.assign(
          {
            unitName: 'BBBBBB',
            basePoint: 1,
            totalPoint: 1,
            m: 'BBBBBB',
            cc: 'BBBBBB',
            ct: 'BBBBBB',
            f: 'BBBBBB',
            e: 'BBBBBB',
            pv: 'BBBBBB',
            a: 'BBBBBB',
            cd: 'BBBBBB',
            sv: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UnitLbr', () => {
        const returnedFromService = Object.assign(
          {
            unitName: 'BBBBBB',
            basePoint: 1,
            totalPoint: 1,
            m: 'BBBBBB',
            cc: 'BBBBBB',
            ct: 'BBBBBB',
            f: 'BBBBBB',
            e: 'BBBBBB',
            pv: 'BBBBBB',
            a: 'BBBBBB',
            cd: 'BBBBBB',
            sv: 'BBBBBB'
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

      it('should delete a UnitLbr', () => {
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
