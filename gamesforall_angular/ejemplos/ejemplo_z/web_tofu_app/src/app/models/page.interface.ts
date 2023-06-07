export interface Page<T> {
    content:       T[];
    last:          boolean;
    first:         boolean;
    totalPages:    number;
    totalElements: number;
}
