import { IUnitMapLbr } from 'app/shared/model/unit-map-lbr.model';

export function computeGearPoint(unitMap: IUnitMapLbr): number {
  if (unitMap && unitMap.gears) {
    let result = 0;
    for (const gear of unitMap.gears) {
      result += gear.pointValue ? gear.pointValue : 0;
    }
    return result;
  } else {
    return 0;
  }
}
