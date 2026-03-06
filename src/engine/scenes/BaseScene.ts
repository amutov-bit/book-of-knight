export default class BaseScene {
  public readonly name: string;

  constructor(name: string = 'scene') {
    this.name = name;
  }

  show(): void {}

  hide(): void {}

  update(_dt: number): void {}
}
