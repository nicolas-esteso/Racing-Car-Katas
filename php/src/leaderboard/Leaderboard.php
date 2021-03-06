<?php

namespace RacingCar\Leaderboard;

class Leaderboard
{
    /** @var Race[] */
    private array $races;

    /**
     * Leaderboard constructor.
     *
     * @param Race[] $races
     */
    public function __construct(array $races)
    {
        $this->races = $races;
    }

    public function getDriverPoints()
    {
        $driverPoints = [];
        foreach ($this->races as $race) {
            foreach ($race->getResults() as $driver) {
                $name = $race->getDriverName($driver);
                if (!isset($driverPoints[$name])) {
                    $driverPoints[$name] = 0;
                }
                $driverPoints[$name] += $race->getPoints($driver);
            }
        }
        return $driverPoints;
    }

    public function getDriverRankings()
    {
        $points = $this->getDriverPoints();
        arsort($points);
        return array_keys($points);
    }
}

