class Point {
    private double[] attributes;

    public Point(double[] attributes) {
        this.attributes = attributes;
    }

    public double[] getAttributes() {
        return attributes;
    }

    public double distanceTo(Point other) {
        double sum = 0;
        for (int i = 0; i < attributes.length; i++) {
            sum += Math.pow(attributes[i] - other.attributes[i], 2);
        }
        return Math.sqrt(sum);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (double attr : attributes) {
            sb.append(attr).append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append(")");
        return sb.toString();
    }
}