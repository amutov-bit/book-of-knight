/**
 * Runtime assertion helper used in development-safe checks.
 * @param {boolean} condition
 * @param {string} message
 */
export function assert(condition, message) {
    if (condition) return;
    throw new Error(message || 'Assertion failed');
}

export default assert;

