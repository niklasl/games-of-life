"""
Game of life...

1. Any live cell with fewer than two live neighbours dies, as if caused by
under-population:

    >>> world = World()
    >>> world.seed(Cell(0, 0, True))
    >>> world.next()
    >>> world[0].alive
    False

2. Any live cell with two or three live neighbours lives on to the next
generation:

    >>> world = World()
    >>> world.seed(
    ...     Cell(0, 0, True), Cell(1, 0, True), Cell(2, 0, True),
    ... )
    >>> world.next()
    >>> world[1].alive
    True

3. Any live cell with more than three live neighbours dies, as if by
overcrowding:

    >>> world = World()
    >>> world.seed(
    ...     Cell(0, 0, True), Cell(1, 0, True), Cell(2, 0, True),
    ...     Cell(0, 1, True), Cell(1, 1, True), Cell(2, 1, False),
    ... )
    >>> world[1].alive
    True
    >>> world.next()
    >>> world[1].alive
    False

4. Any dead cell with exactly three live neighbours becomes a live cell, as if
by reproduction:

    >>> world = World()
    >>> world.seed(
    ...     Cell(0, 0, True), Cell(1, 0, False), Cell(2, 0, True),
    ...     Cell(0, 1, False), Cell(1, 1, True), Cell(2, 1, False),
    ... )
    >>> world[1].alive
    False
    >>> world.next()
    >>> world[1].alive
    True

"""
__metaclass__ = type
from collections import namedtuple


class World:

    def __init__(self):
        self.cells = None

    def seed(self, *cells):
        self.cells = list(cells)

    def next(self):
        self.cells = [self.next_cell(cell) for cell in self.cells]

    def next_cell(self, cell):
        live_count = sum(1 for n in self.get_neighbours(cell) if n.alive)
        alive = cell.alive and live_count > 1 and live_count < 3 or live_count == 3
        return Cell(cell.x, cell.y, alive)

    def get_neighbours(self, cell):
        return [other for other in self.cells
                if abs(cell.x - other.x) is 1 or abs(cell.y - other.y) is 1]

    def __getitem__(self, i):
        return self.cells[i]


Cell = namedtuple('Cell', 'x y alive')


if __name__ == '__main__':

    import doctest
    doctest.testmod()
    print "Ok!"

