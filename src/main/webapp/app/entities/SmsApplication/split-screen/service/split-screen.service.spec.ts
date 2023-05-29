import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISplitScreen } from '../split-screen.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../split-screen.test-samples';

import { SplitScreenService, RestSplitScreen } from './split-screen.service';

const requireRestSample: RestSplitScreen = {
  ...sampleWithRequiredData,
  updateDate: sampleWithRequiredData.updateDate?.toJSON(),
  createDate: sampleWithRequiredData.createDate?.toJSON(),
};

describe('SplitScreen Service', () => {
  let service: SplitScreenService;
  let httpMock: HttpTestingController;
  let expectedResult: ISplitScreen | ISplitScreen[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SplitScreenService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a SplitScreen', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const splitScreen = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(splitScreen).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SplitScreen', () => {
      const splitScreen = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(splitScreen).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SplitScreen', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SplitScreen', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SplitScreen', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSplitScreenToCollectionIfMissing', () => {
      it('should add a SplitScreen to an empty array', () => {
        const splitScreen: ISplitScreen = sampleWithRequiredData;
        expectedResult = service.addSplitScreenToCollectionIfMissing([], splitScreen);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(splitScreen);
      });

      it('should not add a SplitScreen to an array that contains it', () => {
        const splitScreen: ISplitScreen = sampleWithRequiredData;
        const splitScreenCollection: ISplitScreen[] = [
          {
            ...splitScreen,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSplitScreenToCollectionIfMissing(splitScreenCollection, splitScreen);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SplitScreen to an array that doesn't contain it", () => {
        const splitScreen: ISplitScreen = sampleWithRequiredData;
        const splitScreenCollection: ISplitScreen[] = [sampleWithPartialData];
        expectedResult = service.addSplitScreenToCollectionIfMissing(splitScreenCollection, splitScreen);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(splitScreen);
      });

      it('should add only unique SplitScreen to an array', () => {
        const splitScreenArray: ISplitScreen[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const splitScreenCollection: ISplitScreen[] = [sampleWithRequiredData];
        expectedResult = service.addSplitScreenToCollectionIfMissing(splitScreenCollection, ...splitScreenArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const splitScreen: ISplitScreen = sampleWithRequiredData;
        const splitScreen2: ISplitScreen = sampleWithPartialData;
        expectedResult = service.addSplitScreenToCollectionIfMissing([], splitScreen, splitScreen2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(splitScreen);
        expect(expectedResult).toContain(splitScreen2);
      });

      it('should accept null and undefined values', () => {
        const splitScreen: ISplitScreen = sampleWithRequiredData;
        expectedResult = service.addSplitScreenToCollectionIfMissing([], null, splitScreen, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(splitScreen);
      });

      it('should return initial array if no SplitScreen is added', () => {
        const splitScreenCollection: ISplitScreen[] = [sampleWithRequiredData];
        expectedResult = service.addSplitScreenToCollectionIfMissing(splitScreenCollection, undefined, null);
        expect(expectedResult).toEqual(splitScreenCollection);
      });
    });

    describe('compareSplitScreen', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSplitScreen(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSplitScreen(entity1, entity2);
        const compareResult2 = service.compareSplitScreen(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSplitScreen(entity1, entity2);
        const compareResult2 = service.compareSplitScreen(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSplitScreen(entity1, entity2);
        const compareResult2 = service.compareSplitScreen(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
