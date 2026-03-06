import assert from 'node:assert/strict';
import { formatCentsByPattern, getDefaultNumberPattern } from '../src/utils/numberFormat';

function run() {
    assert.equal(getDefaultNumberPattern(), '#,###.00');

    assert.equal(formatCentsByPattern(123456789, '#,###.00'), '1,234,567.89');
    assert.equal(formatCentsByPattern(123456789, '#.###,00'), '1.234.567,89');
    assert.equal(formatCentsByPattern(123456789, '#.###.00'), '1.234.567.89');
    assert.equal(formatCentsByPattern(123456789, '# ###.00'), '1 234 567.89');
    assert.equal(formatCentsByPattern(123456789, '####.00'), '1234567.89');
    assert.equal(formatCentsByPattern(123456700, '#,###'), '1,234,567');
    assert.equal(formatCentsByPattern(123456700, '#.###'), '1.234.567');
    assert.equal(formatCentsByPattern(123456700, '# ###'), '1 234 567');

    console.log('numberFormat tests: OK');
}

run();
