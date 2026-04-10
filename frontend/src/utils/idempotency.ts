export function createIdempotencyKey(prefix = 'req'): string {
  const randomPart = typeof crypto !== 'undefined' && 'randomUUID' in crypto
    ? crypto.randomUUID()
    : Math.random().toString(36).slice(2)
  return `${prefix}-${Date.now()}-${randomPart}`
}