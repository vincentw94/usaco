# closest distance between a and b on dial
def dist(a, b, N):
    dist = abs(a - b)
    if dist > N / 2:
        dist = N - dist
    return dist

# returns number of digit overlap (within 2)
# between a and b
def overlap(a, b, N):
    d = dist(a, b, N)
    return max(0, 5 - d)

with open('combo.in', 'r') as f:
    N = int(f.readline())
    farmer = [int(val) for val in f.readline().strip().split()]
    master = [int(val) for val in f.readline().strip().split()]

    base = 2 * 5*5*5    # number of combos if no overlap

    common = [overlap(a, b, N) for a, b in zip(farmer, master)]
    common_total = common[0] * common[1] * common[2]
    print common
    print base - common_total

with open('combo.out', 'w') as f2:
    f2.write(str(base - common_total) + '\n')
